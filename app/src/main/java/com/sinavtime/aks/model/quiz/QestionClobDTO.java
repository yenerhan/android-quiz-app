package com.sinavtime.aks.model.quiz;

import java.io.Serializable;
import java.util.List;

public class QestionClobDTO implements Serializable {

    private List<QuestionDTO> qestionDTOList;

    public List<QuestionDTO> getQestionDTOList() {
        return qestionDTOList;
    }

    public void setQestionDTOList(List<QuestionDTO> qestionDTOList) {
        this.qestionDTOList = qestionDTOList;
    }
}
