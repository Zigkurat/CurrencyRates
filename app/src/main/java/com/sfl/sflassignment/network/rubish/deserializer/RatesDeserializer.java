//package com.sfl.sflassignment.network.rubish.deserializer;
//
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParseException;
//import com.sfl.sflassignment.network.rubish.constant.ExchangeType;
//import com.sfl.sflassignment.network.model.ExchangeRates;
//import com.sfl.sflassignment.network.model.ExchangeRate;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class RatesDeserializer implements JsonDeserializer<List<ExchangeRates>> {
//    @Override
//    public List<ExchangeRates> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        List<ExchangeRates> resultList = new ArrayList<>();
//
//        JsonObject object = json.getAsJsonObject();
//        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
//            resultList.add(parseAndGetBankExchangeRates(entry.getKey(), entry.getValue().getAsJsonObject()));
//        }
//
//        return resultList;
//    }
//
//    private ExchangeRates parseAndGetBankExchangeRates(String identifier, JsonObject json) {
//        ExchangeRates result = new ExchangeRates();
//        result.setIdentifier(identifier);
//        result.setTitle(json.get("title").getAsString());
//        result.setDate(json.get("date").getAsLong());
//        result.setLogo(json.get("logo").getAsString());
//        result.setCurrencyRates(parseAndGetCurrencyRates(json.get("list").getAsJsonObject()));
//
//        return result;
//    }
//
//    private Map<String, Map<ExchangeType, ExchangeRate>> parseAndGetCurrencyRates(JsonObject json) {
//        Map<String, Map<ExchangeType, ExchangeRate>> resultMap = new HashMap<>();
//
//        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
//            resultMap.put(entry.getKey(), parseAndGetTypeRateMap(entry.getValue().getAsJsonObject()));
//        }
//
//        return resultMap;
//    }
//
//    private Map<ExchangeType, ExchangeRate> parseAndGetTypeRateMap(JsonObject json) {
//        Map<ExchangeType, ExchangeRate> result = new HashMap<>();
//
//        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
//            ExchangeType type = ExchangeType.valueFrom(entry.getKey());
//            ExchangeRate rate = parseAndGetExchangeRate(entry.getValue().getAsJsonObject());
//
//            result.put(type, rate);
//        }
//
//        return result;
//    }
//
//    private ExchangeRate parseAndGetExchangeRate(JsonObject json) {
//        ExchangeRate result = new ExchangeRate();
//        result.setBuy(json.get("buy").getAsFloat());
//        result.setSell(json.get("sell").getAsFloat());
//
//        return result;
//    }
//}
