package me.mrgeneralq.sleepmost.core.services;

import org.bukkit.World;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class GameRuleServiceTest {

    private static World worldStub(InvocationHandler handler) {
        return (World) Proxy.newProxyInstance(
                World.class.getClassLoader(),
                new Class<?>[]{World.class},
                handler);
    }

    /**
     * On servers without the ADVANCE_TIME game rule, referencing it fails with
     * a {@link LinkageError} (NoSuchFieldError on older APIs; in this test JVM
     * the GameRule registry cannot initialize without a running server, which
     * raises ExceptionInInitializerError - also a LinkageError). Either way the
     * service must swallow it instead of propagating.
     * <p>
     * The happy path (rule actually applied) is covered by the Paper smoke
     * test in CI, which boots a real server with the built plugin.
     */
    @Test
    public void setAdvanceTimeDoesNotPropagateWhenGameRuleUnavailable() {
        World world = worldStub((proxy, method, args) -> {
            if (method.getName().equals("setGameRule")) {
                throw new NoSuchFieldError("ADVANCE_TIME");
            }
            return null;
        });

        new GameRuleService().setAdvanceTime(world, false);
    }
}
