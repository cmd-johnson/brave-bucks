package com.buyback.eve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.buyback.eve.domain.Killmail;

import org.json.JSONArray;
import org.json.JSONObject;

class KillmailParser {

    private KillmailParser() {
    }

    static Optional<Killmail> parseKillmail(final String singleKillmail, final Long characterId) {
        return parseKillmail(new JSONObject(singleKillmail), characterId);
    }

    private static Optional<Killmail> parseKillmail(final JSONObject object, final Long characterId) {
        Killmail result = new Killmail();
        result.setCharacterId(characterId);
        result.setKillId(object.getLong("killID"));
        result.setSolarSystemId(object.getLong("solarSystemID"));
        result.setKillTime(object.getString("killTime"));
        result.setAttackerCount(object.getJSONArray("attackers").length());
        result.setNpc(object.getJSONObject("zkb").getBoolean("npc"));
        result.setTotalValue(object.getJSONObject("zkb").getLong("totalValue"));
        result.setPoints(object.getJSONObject("zkb").getLong("points"));
        return Optional.of(result);
    }

    static List<Killmail> parseKillmails(final String killmailArray, final Long characterId) {
        List<Killmail> killmails = new ArrayList<>();
        JSONArray array = new JSONArray(killmailArray);
        for (int i = 0; i < array.length(); i++) {
            parseKillmail(array.getJSONObject(i), characterId).ifPresent(killmails::add);
        }
        return killmails;
    }
}
