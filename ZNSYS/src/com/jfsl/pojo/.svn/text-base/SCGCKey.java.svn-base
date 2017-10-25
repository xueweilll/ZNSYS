package com.jfsl.pojo;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

    @Embeddable  
    public class SCGCKey  implements Serializable{  

        private static final long serialVersionUID = -3304319243957837925L; 
        
    	/*关键字属性*/
    	@Column(name = "XMZMC")
    	private String xmzmc;
    	@Column(name = "JCXM")
    	private String jcxm;
    	@Column(name = "CSNAME")
    	private String csname;
    	@Column(name = "SCGC")
    	private String scgc;
    	@Column(name = "JCXMGC")
    	private String jcxmgc;
      
        public String getXmzmc() {
			return xmzmc;
		}

		public void setXmzmc(String xmzmc) {
			this.xmzmc = xmzmc;
		}

		public String getJcxm() {
			return jcxm;
		}

		public void setJcxm(String jcxm) {
			this.jcxm = jcxm;
		}

		public String getCsname() {
			return csname;
		}

		public void setCsname(String csname) {
			this.csname = csname;
		}

		public String getScgc() {
			return scgc;
		}

		public void setScgc(String scgc) {
			this.scgc = scgc;
		}

		public String getJcxmgc() {
			return jcxmgc;
		}

		public void setJcxmgc(String jcxmgc) {
			this.jcxmgc = jcxmgc;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		/*重写匹配的方法*/
		@Override  
        public boolean equals(Object o) {  
            if(o instanceof SCGCKey){  
                SCGCKey key = (SCGCKey)o ;  
                if(this.csname.equals(key.getCsname()) && this.jcxm.equals(key.getJcxm()) && this.jcxmgc.equals(key.getJcxmgc()) && this.scgc.equals(key.getScgc()) && this.xmzmc.equals(key.getXmzmc())){  
                    return true ;  
                }  
            }  
            return false ;  
        }  
        
		/*哈希的方法只是为了将关键值进行一个数字计算，以区别不同的对象，对于计算方法没有特殊要求*/
        @Override  
        public int hashCode() {  
            return this.csname.hashCode() * this.jcxm.hashCode() * this.jcxmgc.hashCode() * this.scgc.hashCode() * this.xmzmc.hashCode();  
        }  
          
    }  