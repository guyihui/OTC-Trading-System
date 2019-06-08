package tradergateway.gateway.Entity;

public class User {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && ((User) obj).getName().equals(name);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + name.hashCode();
    }
}
