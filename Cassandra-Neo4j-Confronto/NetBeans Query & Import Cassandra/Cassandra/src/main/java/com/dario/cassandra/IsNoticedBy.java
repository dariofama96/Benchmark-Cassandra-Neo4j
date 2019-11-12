
package com.dario.cassandra;

import java.io.Serializable;

public class IsNoticedBy implements Serializable {
    public String contractNumber;
    public String title;
    public String num_offers; 
    public String idnoticecan;
    public String typeofcontract;
    
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
  public String getIdNoticeCan()
    {
     return this.idnoticecan;
    }
    public void setIdNoticeCan(String mn)
    {
     this.idnoticecan = mn;
    }

    public String getTypeOfContract()
    {
     return this.typeofcontract;
    }

    public void setTypeOfContract(String tw)
    {
     this.typeofcontract = tw;
    }
}
