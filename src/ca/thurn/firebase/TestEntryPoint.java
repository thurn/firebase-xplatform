package ca.thurn.firebase;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Timer;

class TestEntryPoint implements EntryPoint {

  @Override
  public void onModuleLoad() {
    ScriptInjector.fromUrl("http://cdn.firebase.com/v0/firebase.js")
        .setCallback(new Callback<Void, Exception>() {
          @Override
          public void onFailure(Exception reason) {}

          @Override
          public void onSuccess(Void result) {
            Firebase fb = new Firebase("https://gwt.firebaseio.com/foo");
//            List<Object> objects = new ArrayList<Object>();
//            objects.add(234.2);
//            objects.add("bar2");
//            fb.addListenerForSingleValueEvent(new ValueEventListener() {
//              @Override
//              public void onCancelled() {}
//
//              @Override
//              public void onDataChange(DataSnapshot snapshot) {
//                @SuppressWarnings("unchecked")
//                List<Double> list = (List<Double>) snapshot.getValue();
//                log("should be 'bar2': <" + list.get(1) + "> should be 234.2 <" + list.get(0) + ">");
//              }
//            });
//            CompletionListener onComplete = new CompletionListener() {
//              @Override
//              public void onComplete(FirebaseError error) {
//                log("on complete ran");
//              }
//            };
//            fb.setValue(objects, onComplete);
//            Firebase fb2 = new Firebase("https://gwt.firebaseio.com/bar").push();
//            fb2.addListenerForSingleValueEvent(new ValueEventListener() {
//              @Override
//              public void onCancelled() {}
//
//              @Override
//              public void onDataChange(DataSnapshot snapshot) {
//                Map<String, Object> map =
//                    snapshot.getValue(new GenericTypeIndicator<Map<String, Object>>() {});
//                log("Contains pushed values: "
//                    + (map.containsValue("value") && map.containsValue(16.0)));
//              }
//            });
//            fb2.push().setValue(("value"));
//            fb2.push().setValue(16.0);
//
            final Firebase c = fb.child("child");
            fb.addChildEventListener(new ChildEventListener() {

              @Override
              public void onCancelled() {
                // TODO Auto-generated method stub

              }

              @Override
              public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                System.out.println("child added");
              }

              @Override
              public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                // TODO Auto-generated method stub

              }

              @Override
              public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
                // TODO Auto-generated method stub

              }

              @Override
              public void onChildRemoved(DataSnapshot snapshot) {
                System.out.println("child removed");
              }
            });

            c.setValue("value");
            (new Timer() {
              @Override
              public void run() {
                c.removeValue();
              }
            }).schedule(2000);

            clearAll();
          }
        }).inject();
  }

  private void clearAll() {
    (new Timer() {
      @Override
      public void run() {
        new Firebase("https://gwt.firebaseio.com/").setValue(null);
      }
    }).schedule(5000);
  }

  private native void log(String msg) /*-{
		$wnd.console.log(msg);
	}-*/;
}
