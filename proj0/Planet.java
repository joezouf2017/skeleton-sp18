public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double g = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) + (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p) {
        return g * this.mass * p.mass / (calcDistance(p) * calcDistance(p));
    }

    public double calcForceExertedByX(Planet p) {
        double F = calcForceExertedBy(p);
        double dx = p.xxPos - xxPos;
        return F * dx / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        double F = calcForceExertedBy(p);
        double dy =  p.yyPos - yyPos;
        return F * dy / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] p) {
        double F = 0;
        for (Planet i : p) {
            if (this.equals(i)) {
                continue;
            }
            F += calcForceExertedByX(i);
        }
        return F;
    }

    public double calcNetForceExertedByY(Planet[] p) {
        double F = 0;
        for (Planet i : p) {
            if (this.equals(i)) {
                continue;
            }
            F += calcForceExertedByY(i);
        }
        return F;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel = xxVel + ax * dt;
        yyVel = yyVel + ay * dt;
        xxPos = xxPos + xxVel * dt;
        yyPos = yyPos + yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
