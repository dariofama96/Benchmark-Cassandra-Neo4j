
package com.dario.cassandra;

import java.io.Serializable;

/**
 *
 * @author Randomness
 */
public class Authority implements Serializable {
    
    public String mainActivity;
    public String caetown;
    public String caename;
    public String caetype;
    
     /////// constructors ////////////////////
    public Authority()
    {
        
    }

  //////////// methods ///////////////////////
  public String getMainActivity()
  {
     return this.mainActivity;
  }
  public void setMainActivity(String mn)
  {
     this.mainActivity = mn;
  }

  public String getCaeTown()
  {
     return this.caetown;
  }

  public void setCaeTown(String tw)
  {
     this.caetown = tw;
  }

  public String getCaeName()
  {
     return this.caename;
  
  }
  public void setCaeName(String na)
  {
      this.caename = na;
  }
  
  public String getCaeType()
  {
     return this.caetype;
  }
  
  public void setCaeType(String no)
  {
      this.caetype = no;
  }
  
  
}
