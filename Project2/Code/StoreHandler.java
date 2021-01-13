import org.apache.thrift.TException;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.util.Map;

public class StoreHandler implements StoreOperations.Iface {

  private Map<String, String> store;

  public StoreHandler() {

    store = new HashMap<>();
    prepopulate();
  }

  private void prepopulate() {

    this.store.put("beef", "100");
    this.store.put("pork", "10");
    this.store.put("chocolate", "15");
    this.store.put("noodle", "15");
    this.store.put("pizza", "20");
    this.store.put("ice cream", "5");
  }

  @Override
  public String analyze(String command) throws InvalidOperation {

    String[] arr = command.trim().split("\\s+");
    String op = arr[0].toLowerCase();

    if (op.equals("get")) {

      if (arr.length != 2) {

        InvalidOperation io = new InvalidOperation();
        Operation operation = Operation.GET;
        io.what = operation.getValue();
        io.why = "malformed get request";
        System.out.println("received malformed get request");
        System.out.println();
        throw io;

      } else {

        String key = arr[1].toLowerCase();
        return dealGet(key);
      }

    } else if (op.equals("put")) {

      if (arr.length != 3) {

        InvalidOperation io = new InvalidOperation();
        Operation operation = Operation.PUT;
        io.what = operation.getValue();
        io.why = "malformed put request";
        System.out.println("received Malformed put request");
        System.out.println();
        throw io;
      } else {

        String key = arr[1];
        String value = arr[2];
        return dealPUT(key, value);

      }
    } else if (op.equals("delete")) {

      if (arr.length != 2) {

        InvalidOperation io = new InvalidOperation();
        Operation operation = Operation.DELETE;
        io.what = operation.getValue();
        io.why = "malformed delete request";
        System.out.println("received malformed delete request");
        System.out.println();
        throw io;

      } else {

        return dealDELETE(arr[1].toLowerCase());
      }
    } else {

      InvalidOperation io = new InvalidOperation();
      Operation operation = Operation.Unknown;
      io.what = operation.getValue();
      io.why = "Unknown operations";
      System.out.println("received an unknown operation");
      System.out.println();

      throw io;

    }
  }

  @Override
  public String dealGet(String key) throws InvalidOperation {

    key = key.toLowerCase();
    if (this.store.containsKey(key)) {
      System.out.println("GET query is complete");
      showTime();
      return this.store.get(key);
    }

    String ans = key + " does not exist in the store";
    System.out.println(ans);
    showTime();
    return ans;

  }

  @Override
  public String dealPUT(String key, String value) throws InvalidOperation {

    //this.store.putIfAbsent(key, value);
    String error = "Received a malformed put request";
    boolean isNumeric;

    try {

      Double.parseDouble(value);
      isNumeric = true;
    } catch (NumberFormatException e) {
      isNumeric = false;
    }

    if (!isNumeric) {

      System.out.println(error);
      return error;
    }

    boolean isNotNumeric;
    try {
      Double.parseDouble(key);
      isNotNumeric = false;
    } catch (NumberFormatException e) {
      isNotNumeric = true;
    }
    if (!isNotNumeric) {

      System.out.println(error);
      return error;
    }

    key = key.toLowerCase();

    this.store.put(key, value);

    System.out.println("PUT query is complete!!");
    showTime();
    return "PUT query is complete!!";

  }

  @Override
  public String dealDELETE(String key) throws InvalidOperation {

    key = key.toLowerCase();
    if (this.store.containsKey(key)) {

      this.store.remove(key);
      System.out.println("DELETE query is complete");
      showTime();
      return "DELETE query is complete";
    }

    System.out.println("This food does not exist");
    showTime();
    return "This food does not exist";

  }

  /**
   * This function shows the current system time in millisecond precision.
   */

  public void showTime() throws InvalidOperation {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    System.out.print("System Time:  ");
    System.out.println(formatter.format(System.currentTimeMillis()));
  }
}
