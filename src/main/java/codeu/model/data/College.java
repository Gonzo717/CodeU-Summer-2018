public enum College {
        BU("Boston University "),
        Rice("Rice University"),
        UIC("University of Illinois at Chicago");

        private String collegeName;
        private College(String name) {
            this.collegeName = name;
        }

        @Override
        public String toString(){
            return collegeName;
        }
    }
