package task.dto;

/**
* グループのデータを表すDTOクラス
*/
public class GroupsDTO {

   // フィールド定義
   private int groupId; // グループID
   private String groupName; // グループ名
   

   // デフォルトコンストラクタ
   public GroupsDTO() {
   }

   // コンストラクタ オーバーロード
   public GroupsDTO(int groupId, String groupName) {
       this.groupId = groupId;
       this.groupName = groupName;
   }

   // ゲッターとセッター
   public int getGroupId() {
       return groupId;
   }

   public void setGroupId(int groupId) {
       this.groupId = groupId;
   }

   public String getGroupName() {
       return groupName;
   }

   public void setGroupName(String groupName) {
       this.groupName = groupName;
   }

   
   
   @Override
   public String toString() {
       return "GroupsDTO{" +
               "groupId=" + groupId +
               ", groupName='" + groupName + '\'' +
               
               '}';
   }
}