package xyz.mydev.common.beans.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author ZSP
 */
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class MarkedSerializedTypeStudentVO {
  private String studentName;
  private String studentGrade;
  private String studentAddress;
  private Long timestamp;

  public static MarkedSerializedTypeStudentVO copy(StudentVO studentVO) {
    if (studentVO == null) {
      return null;
    }

    MarkedSerializedTypeStudentVO markedSerializedTypeStudentVO = new MarkedSerializedTypeStudentVO();

    markedSerializedTypeStudentVO.setStudentAddress(studentVO.getStudentAddress());
    markedSerializedTypeStudentVO.setStudentGrade(studentVO.getStudentGrade());
    markedSerializedTypeStudentVO.setStudentName(studentVO.getStudentName());
    markedSerializedTypeStudentVO.setTimestamp(studentVO.getTimestamp());

    return markedSerializedTypeStudentVO;

  }
}
