//package com.vinhome.residentmanagement.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Embeddable
//public class UserHistoryKey implements Serializable {
//    @Column(name = "history_id")
//    private Long historyId;
//
//    @Column(name = "receiver_id")
//    private Long receiverId;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserHistoryKey that = (UserHistoryKey) o;
//        return Objects.equals(historyId, that.historyId) &&
//                Objects.equals(receiverId, that.receiverId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(historyId, receiverId);
//    }
//}
