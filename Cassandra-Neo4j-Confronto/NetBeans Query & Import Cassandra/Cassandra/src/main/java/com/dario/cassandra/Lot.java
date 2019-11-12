package com.dario.cassandra;

import java.io.Serializable;

public class Lot implements Serializable{
    
    public String idLotAwarded;
    //////////// methods ///////////////////////
  public String getIdLotAwarded()
  {
     return this.idLotAwarded;
  }
  public void setIdLotAwarded(String id)
  {
     this.idLotAwarded = id;
  }

    
}
