package com.dario.cassandra;

import java.io.Serializable;


public class IsContractAwarded implements Serializable {
    
    public String contractNumber;
    public String title;
    public String num_offers; 
    public String idAward;
    public String wintown;
    public String winname;
    public String wincountrycode;
    public float contractPrice;
  //////////// methods ///////////////////////
  public String getContractNumber()
  {
     return this.contractNumber;
  }
  public void setContractNumber(String cN)
  {
     this.contractNumber = cN;
  }

  public String getTitle()
  {
     return this.title;
  }

  public void setTitle(String tT)
  {
     this.title = tT;
  }

  public String getNumberOffers()
  {
     return this.num_offers;
  }
  public void setNumberOffers(String no)
  {
      this.num_offers = no;
  }
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
