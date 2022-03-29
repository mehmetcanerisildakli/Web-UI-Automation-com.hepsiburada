package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElementInfo {


  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("value")
  @Expose
  private String value;
  @SerializedName("type")
  @Expose
  private String type;

  public ElementInfo(){

  }

  public String getKey(){
    return key;
  }

  public String getValue(){
    return value;
  }

  public String getType(){
    return type;
  }


}
