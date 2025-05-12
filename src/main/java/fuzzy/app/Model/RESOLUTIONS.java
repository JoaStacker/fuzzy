package fuzzy.app.Model;

public enum RESOLUTIONS {
    VERY_LOW(){
        @Override
        public String toString() {
            return "240p";
        }
    },
    LOW(){
        @Override
        public String toString() {
            return "360p";
        }
    },
    MEDIUM(){
        @Override
        public String toString() {
            return "480p";
        }
    },
    HIGH(){
        @Override
        public String toString() {
            return "720p";
        }
    },
    VERY_HIGH(){
        @Override
        public String toString() {
            return "1080p";
        }
    },
}