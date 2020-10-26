package xyz.mydev.baidu.ai.face;

import xyz.mydev.baidu.ai.face.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.client.bean.CommonResult;
import xyz.mydev.baidu.ai.face.client.bean.MatchResult;
import xyz.mydev.baidu.ai.face.client.bean.SearchBatchResult;
import xyz.mydev.baidu.ai.face.client.bean.SearchSingleResult;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceInfo;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceMatchInfo;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceSearchInfo;

import java.util.List;

/**
 * @author ZSP
 */
public interface IBaiduAiFaceApiClientAdapter {
  AddUserResult addUser(UserFaceInfo userFaceInfo);

  AddUserResult updateUser(UserFaceInfo userFaceInfo);

  CommonResult faceDelete(String userId, String groupId, String faceToken);

  SearchBatchResult searchBatch(UserFaceSearchInfo info);

  SearchSingleResult searchSingle(UserFaceSearchInfo info);

  MatchResult match(List<UserFaceMatchInfo> input);
}
