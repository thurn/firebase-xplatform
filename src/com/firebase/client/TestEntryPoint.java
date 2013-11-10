package com.firebase.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;

class TestEntryPoint implements EntryPoint {

  @Override
  public void onModuleLoad() {
    Callback<Void, Exception> callback = new Callback<Void, Exception>() {
      @Override
      public void onFailure(Exception reason) {
        throw new RuntimeException(reason);
      }
    
      @Override
      public void onSuccess(Void result) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("one", "two");
        List<Object> list = new ArrayList<Object>();
        list.add("one");
        list.add(2.222);
        map.put("list", list);
        Firebase firebase = new Firebase("https://gwt-wrapper.firebaseio-demo.com/");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {

          @Override
          public void onCancelled() {
            throw new RuntimeException("Cancelled!?");
          }

          @Override
          public void onDataChange(DataSnapshot snapshot) {
            Map<String, Object> newMap = snapshot.getValue(
                new GenericTypeIndicator<Map<String,Object>>());
            Window.alert(newMap.get("one").toString());
          }
          
        });
        firebase.setValue(map);
      }
    };
    ScriptInjector
        .fromUrl("https://cdn.firebase.com/v0/firebase.js")
        .setCallback(callback)
        .inject();
  }
}
