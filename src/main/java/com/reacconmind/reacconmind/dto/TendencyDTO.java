package com.reacconmind.reacconmind.dto;

import java.sql.Date;

public class TendencyDTO {
    private int idTendency;
    private String name;
    private Date dateBegin;
    private Date dateEnd;
    
    public int getIdTendency() {
        return idTendency;
    }
    public void setIdTendency(int idTendency) {
        this.idTendency = idTendency;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDateBegin() {
        return dateBegin;
    }
    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }
    public Date getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    
}
