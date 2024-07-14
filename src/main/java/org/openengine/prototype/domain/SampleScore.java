package org.openengine.prototype.domain;

public class SampleScore {
    public class TeamInfo {
        private double homeStrength;
        private double awayStrength;

        public TeamInfo(double homeStrength, double awayStrength) {
            this.homeStrength = homeStrength;
            this.awayStrength = awayStrength;
        }

        public double getHomeStrength() { return homeStrength; }
        public double getAwayStrength() { return awayStrength; }
    }

    public class ScoreInfo {
        private int homeScore;
        private int awayScore;
        private boolean neutral;

        public ScoreInfo(int homeScore, int awayScore, boolean neutral) {
            this.homeScore = homeScore;
            this.awayScore = awayScore;
            this.neutral = neutral;
        }

        public int getHomeScore() { return homeScore; }
        public int getAwayScore() { return awayScore; }
        public boolean isNeutral() { return neutral; }
    }

    public class GameParameters {
        private TeamInfo teamInfo;
        private ScoreInfo scoreInfo;
        private double distance;

        public GameParameters(TeamInfo teamInfo, ScoreInfo scoreInfo) {
            this.teamInfo = teamInfo;
            this.scoreInfo = scoreInfo;
        }

        public TeamInfo getTeamInfo() { return teamInfo; }
        public ScoreInfo getScoreInfo() { return scoreInfo; }
        public double getDistance() { return this.distance; }

        public String toString() {
            return "Home strength: " + this.teamInfo.getHomeStrength() + ", " +
                    "Away strength: " + this.teamInfo.getAwayStrength() + ", " +
                    "Home score: " + this.scoreInfo.getHomeScore() + ", " +
                    "Away score: " + this.scoreInfo.getAwayScore() + ", " +
                    "Neutral: " + this.scoreInfo.isNeutral();
        }

        private double squaredDistance(double a, double f) {
            return Math.pow(a - f, 2);
        }

        private double hypotenuse(double sqrX, double sqrY) {
            return Math.sqrt(sqrX + sqrY);
        }

        public void calculateDistance(double homeStrength, double awayStrength, boolean neutral) {

            double homeSquaredDistance = squaredDistance(homeStrength, this.teamInfo.getHomeStrength());
            double awaySquaredDistance = squaredDistance(awayStrength, this.teamInfo.getAwayStrength());

            double totalDistance = hypotenuse(homeSquaredDistance, awaySquaredDistance);

            if (neutral) {
                totalDistance = calculateNeutralDistance(homeStrength, awayStrength, totalDistance);
            }

            this.distance = totalDistance;
        }

        public void calculateHomeDistance(double homeStrength, boolean neutral) {
            calculateSingleDistance(homeStrength, neutral, true);
        }

        public void calculateAwayDistance(double awayStrength, boolean neutral) {
            calculateSingleDistance(awayStrength, neutral, false);
        }

        public void calculateClosest(double strength, boolean filterOnHome) {
            calculateSingleDistance(strength, true, filterOnHome);
        }

        public void calculateSingleDistance(double strength, boolean neutral, boolean filterOnHome) {

            double distance = squaredDistance(strength, filterOnHome ? this.teamInfo.getHomeStrength() : this.teamInfo.getAwayStrength());

            if (neutral) {

                double reverseDistance = squaredDistance(strength, filterOnHome ? this.teamInfo.getAwayStrength() : this.teamInfo.getHomeStrength());

                if (reverseDistance < distance) {
                    distance = reverseDistance;
                }
            }

            this.distance = distance;
        }

        private double calculateNeutralDistance(double homeStrength, double awayStrength, double totalDistance) {

            double homeToAwayDistance = squaredDistance(homeStrength, this.teamInfo.getAwayStrength());
            double awayToHomeDistance = squaredDistance(awayStrength, this.teamInfo.getHomeStrength());

            double reverseDistance = squaredDistance(homeToAwayDistance, awayToHomeDistance);

            if (reverseDistance < totalDistance) {
                totalDistance = reverseDistance;
            }

            return totalDistance;
        }

    }
}
