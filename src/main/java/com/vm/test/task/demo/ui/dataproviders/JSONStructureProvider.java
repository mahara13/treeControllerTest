package com.vm.test.task.demo.ui.dataproviders;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.vm.test.task.demo.ui.model.InternalTreeNode;
import com.vm.test.task.demo.ui.model.ITreeNode;
import com.vm.test.task.demo.ui.events.TreeEvent;
import com.vm.test.task.demo.ui.events.TreeEventType;
import com.vm.test.task.demo.uiplatform.EventManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

public class JSONStructureProvider implements TreeStructureProvider {

    private EventManager dispatcher;

    @Override
    public InternalTreeNode parseData(Object jsonFile, EventManager dispatcher) {
        this.dispatcher = dispatcher;
        return internalParseData((File)jsonFile, dispatcher);
    }

    private InternalTreeNode internalParseData(File jsonFile, EventManager dispatcher) {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            JsonElement array = gson.fromJson(reader, JsonElement.class);
            InternalTreeNode rootCandidate = new ITreeNode();
            if (array.isJsonArray()) {
                parseLevel(rootCandidate, (JsonArray) array);
            }
            return rootCandidate;
        } catch (FileNotFoundException e) {
            dispatchEvent(TreeEventType.UNSUPPORTED_JSON_TYPE);
        }
        return null;
    }


    private void parseLevel(InternalTreeNode levelParent, JsonArray levelChildren) {
        for (JsonElement je:levelChildren) {
            if (je.isJsonPrimitive()) {
                levelParent.getChildren().add(new ITreeNode(je.getAsString(), levelParent));
            } else if (je.isJsonObject()) {
                JsonObject jo = (JsonObject)je;
                Set<String> keys = jo.keySet();
                if (keys.size() == 1) {
                    for (String key: keys) {
                        InternalTreeNode parent = new ITreeNode(key, levelParent);
                        levelParent.getChildren().add(parent);
                        JsonElement parentJE = jo.get(key);
                        if (parentJE.isJsonArray()) {
                            parseLevel(parent, parentJE.getAsJsonArray());
                        } else {
                            dispatchEvent(TreeEventType.UNSUPPORTED_JSON_TYPE);
                            return;
                        }
                    }
                } else {
                    dispatchEvent(TreeEventType.UNSUPPORTED_JSON_TYPE);
                    return;
                }
            }
        }
    }

    private void dispatchEvent(TreeEventType eventType) {
        dispatcher.dispatchEvent(new TreeEvent(eventType));
    }
}
