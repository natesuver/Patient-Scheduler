package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class Address {
        protected String add1;
        protected String add2;
        protected String city;
        protected String st;
        protected String zip;
        public String getAddress1() {
            return add1;
        }

        public void setAddress1(String address) {
            this.add1 = address;
        }

        public String getAddress2() {
            return add2;
        }

        public void setAddress2(String address2) {
            this.add2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return st;
        }

        public void setState(String state) {
            this.st = state;
        }

        public String getPostalCode() {
            return zip;
        }

        public void setPostalCode(String postalCode) {
            this.zip = postalCode;
        }

}
