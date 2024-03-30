//package com.vinhome.residentmanagement.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_history")
//public class UserHistory {
//    @EmbeddedId
//    private UserHistoryKey id;
//
//    @ManyToOne
//    @MapsId("historyId")
//    @JoinColumn(name = "history_id")
//    private History history;
//
//    @ManyToOne
//    @MapsId("receiverId")
//    @JoinColumn(name = "receiver_id")
//    private User receiver;
//
//    @Column(name = "creator_id", nullable = false)
//    private Long creatorId;
//
//    @Column(nullable = false, name = "read_status",columnDefinition = "boolean default false")
//    private boolean readStatus;
//}
