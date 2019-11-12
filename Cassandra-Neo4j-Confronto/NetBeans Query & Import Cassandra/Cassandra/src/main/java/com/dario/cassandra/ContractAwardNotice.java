/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dario.cassandra;

import java.io.Serializable;

/**
 *
 * @author Dario
 */
public class ContractAwardNotice implements Serializable {
    public String idnoticecan;
    public String typeofcontract;
    
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
