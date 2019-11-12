
package com.dario.cassandra;

import java.io.Serializable;


public class ContractAward implements Serializable {
   public String idAward;
    public String wintown;
    public String winname;
    public String wincountrycode;
    public float contractPrice;
    
     /////// constructors ////////////////////
    public ContractAward()
    {
        
    }

  //////////// methods ///////////////////////
  public String getIdAward()
  {
     return this.idAward;
  }
  public void setIdAward(String mn)
  {
     this.idAward = mn;
  }

  public String getWinTown()
  {
     return this.wintown;
  }

  public void setWinTown(String tw)
  {
     this.wintown = tw;
  }

  public String getWinName()
  {
     return this.winname;
  
  }
  public void setWinName(String na)
  {
      this.winname = na;
  }
  
  public String getWinCountryCode()
  {
     return this.wincountrycode;
  }
  
  public void setWinCountryCode(String no)
  {
      this.wincountrycode = no;
  }
  
  public float getContractPrice(){
      return this.contractPrice;
  }
  
  public void setContractPrice(float cn){
      this.contractPrice = cn;
  }
}
