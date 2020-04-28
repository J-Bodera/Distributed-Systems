package edu.agh.lab2rest;

import com.google.common.math.Quantiles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@org.springframework.stereotype.Controller
public class Controller {

    final static ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping(value = "/input", produces = MediaType.TEXT_HTML_VALUE)
    public String input(@RequestParam(name = "currency") String currency, @RequestParam(name = "topCount") Integer topCount, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end, Model model) {
        Map<String, String> res = getData(currency, topCount, start, end);

        model.addAttribute("currency", currency);
        model.addAttribute("current", res.get("cur"));
        model.addAttribute("topCount", topCount);
        model.addAttribute("avg", res.get("avg"));
        model.addAttribute("min", res.get("min"));
        model.addAttribute("max", res.get("max"));
        model.addAttribute("med", res.get("med"));

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("avgD", res.get("avgD"));
        model.addAttribute("minD", res.get("minD"));
        model.addAttribute("maxD", res.get("maxD"));
        model.addAttribute("medD", res.get("medD"));

        return "result";
    }

    @GetMapping(value = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String resultJson(@RequestParam(name = "currency") String currency, @RequestParam(name = "topCount") Integer topCount,@RequestParam(name = "start") String start, @RequestParam(name = "end") String end, Model model) {
        Map<String, String> stats = getData(currency, topCount, start, end);
        stats.put("currency", currency);
        stats.put("topCount", topCount.toString());
        stats.put("start", start.toString());
        stats.put("end", end.toString());

        return new JSONObject(stats).toString();
    }

    @GetMapping("/")
    public String init() {
        return "init";
    }

    private Map<String, String> getData(String currency, int topCount, String start, String end) {
        final List<Double> rates = new CopyOnWriteArrayList<>();
        final List<Double> cur = new CopyOnWriteArrayList<>();
        final List<Double> ratesDate = new CopyOnWriteArrayList<>();

        List<Runnable> tasks = new ArrayList<>();

        tasks.add(() -> {
            final RestTemplate restTemplate = new RestTemplate();
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("api.nbp.pl/api/exchangerates/rates")
                    .path("/A")
                    .path("/" + currency)
                    .path("/last")
                    .path("/" + topCount)
                    .queryParam("format", "json")
                    .build();
            JSONObject jsonObject = new JSONObject(restTemplate.getForObject(uriComponents.toString(), String.class));
            JSONArray jsonArray = jsonObject.getJSONArray("rates");
            if(jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0; i<len; i++) {
                    JSONObject tmp = jsonArray.getJSONObject(i);
                    rates.add((Double) tmp.get("mid"));
                }
            }
        });

        tasks.add(() -> {
            final RestTemplate restTemplate = new RestTemplate();
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("api.nbp.pl/api/exchangerates/rates")
                    .path("/A")
                    .path("/" + currency)
                    .path("/last/1")
                    .queryParam("format", "json")
                    .build();
            JSONObject jsonObject = new JSONObject(restTemplate.getForObject(uriComponents.toString(), String.class));
            JSONArray jsonArray = jsonObject.getJSONArray("rates");
            if(jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0; i<len; i++) {
                    JSONObject tmp = jsonArray.getJSONObject(i);
                    cur.add((Double) tmp.get("mid"));
                }
            }
        });

        tasks.add(() -> {
            final RestTemplate restTemplate = new RestTemplate();
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("api.nbp.pl/api/exchangerates/rates")
                    .path("/A")
                    .path("/" + currency)
                    .path("/" + start)
                    .path("/" + end)
                    .queryParam("format", "json")
                    .build();
            JSONObject jsonObject = new JSONObject(restTemplate.getForObject(uriComponents.toString(), String.class));
            JSONArray jsonArray = jsonObject.getJSONArray("rates");
            if(jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0; i<len; i++) {
                    JSONObject tmp = jsonArray.getJSONObject(i);
                    ratesDate.add((Double) tmp.get("mid"));
                }
            }
        });

        CompletableFuture<?>[] futures = tasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executorService))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();

        Collections.sort(rates);

        Map<String, String> stats = new HashMap<>();
        stats.put("avg", round((Double) rates.stream().mapToDouble(a -> (Double) a).average().getAsDouble()).toString());
        stats.put("min", rates.get(0).toString());
        stats.put("max", rates.get(rates.size() - 1).toString());
        stats.put("med", round((Double) Quantiles.median().compute(rates)).toString());
        stats.put("cur", cur.get(0).toString());
        stats.put("avgD", round((Double) ratesDate.stream().mapToDouble(a -> (Double) a).average().getAsDouble()).toString());
        stats.put("minD", ratesDate.get(0).toString());
        stats.put("maxD", ratesDate.get(ratesDate.size() - 1).toString());
        stats.put("medD", round((Double) Quantiles.median().compute(ratesDate)).toString());

        return stats;
    }

    private Double round(Double x) {
        Double res = BigDecimal.valueOf(x).setScale(4, RoundingMode.HALF_UP).doubleValue();
        return res;
    }
}
