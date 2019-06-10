package tradergateway.gateway.Entity;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && ((User) obj).getName().equals(name);
    }

    @Override
    public int hashCode() {
        return "user".hashCode() + name.hashCode();
    }
}
