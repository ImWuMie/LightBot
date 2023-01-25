package qwq.wumie.module.impl.register;

public class User {
    String user_id;
    String perm;
    int bal;

    public User(String user_id, Prem perm, int bal) {
        this.user_id = user_id;
        this.perm = perm.name();
        this.bal = bal;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public enum Prem {
        Member,
        Admin,
        Owner;

        public static Prem get(String name) {
            for (Prem p : values()) {
                if (p.name().equalsIgnoreCase(name)) {
                    return p;
                }
            }
            return null;
        }
    }
}
