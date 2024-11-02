package com.reacconmind.reacconmind.dto;

public class ReactionDTO {


        private String userName;
        private String imageProfile;

        public ReactionDTO() {
        }

        public ReactionDTO(String userName, String imageProfile) {
            this.userName = userName;
            this.imageProfile = imageProfile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getImageProfile() {
            return imageProfile;
        }

        public void setImageProfile(String imageProfile) {
            this.imageProfile = imageProfile;
        }


}
