package com.spacekuukan.application.function;

public class Base extends Planet {

    public static Base instanceBase;

    public InstanceFunction instanceFunction;

    protected int faction;
    protected int space_port;
    protected int total_power;

    public static synchronized Base getInstance(int id, String name, int power, int resource, int latitude, int longitude, int faction) {
        if (instanceBase == null)
            instanceBase = new Base(id, name, power, resource, latitude, longitude, faction);
        return instanceBase;
    }

    public Base(int id, String name, int power, int resource, int latitude, int longitude, int faction) {
        super(id, name, power, resource, latitude, longitude);

        this.faction = faction;

    }

    public void setSpacePort(int space_port) {
        this.space_port = space_port;
    }

    public void setTotalPower(int total_power) {
        this.total_power = total_power;
    }

    public void addHarvesterStarship(int id, String name, int level, int speed, int cost, int mining_rate) {
        instanceFunction.getInstance().starshipList.add(new Harvester(id, name, level, speed, cost, mining_rate));
    }

    public void addStrikerStarship(int id, String name, int level, int speed, int cost, int power) {
        instanceFunction.getInstance().starshipList.add(new Striker(id, name, level, speed, cost, power));
    }

    public void removeStarship(int id) {
        instanceFunction.getInstance().starshipList.remove(id);
    }

    public void upgradeStarship(int id) {
        instanceFunction.getInstance().starshipList.get(id).level++;
    }

    public void addSpacePort() {
        this.space_port++;
        instanceFunction.getInstance().spacePortList.add(new SpacePort(this.space_port, 1));
    }

    public void calculateTotalPower() {
        this.total_power = 0;
        for(int i = 0; i < instanceFunction.getInstance().starshipList.size(); i++) {
            this.total_power += instanceFunction.getInstance().starshipList.get(i).power;
        }
        setTotalPower(this.total_power);
    }
}
