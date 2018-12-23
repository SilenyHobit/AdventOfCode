package advent2018.day16;

import java.util.HashMap;
import java.util.Map;

public class VMStatus {

    private final Map<String, Integer> registers = new HashMap<>();

    public int regiter(String address) {
        return registers.get(address);
    }

    public static VMStatus fromString(String status) {
        String[] numbers = status.replace(",", "").split(" ");
        VMStatus vmStatus = new VMStatus();
        vmStatus.registers.put("0", Integer.parseInt(numbers[0]));
        vmStatus.registers.put("1", Integer.parseInt(numbers[1]));
        vmStatus.registers.put("2", Integer.parseInt(numbers[2]));
        vmStatus.registers.put("3", Integer.parseInt(numbers[3]));
        return vmStatus;
    }

}
