
public class RoomDetail {
 private String roomId;
 private int roomNo;
 private byte[] roompicture;
 private String roomType;
 private double roomPrice;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public byte[] getRoompicture() {
        return roompicture;
    }

    public void setRoompicture(byte[] roompicture) {
        this.roompicture = roompicture;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }
}
