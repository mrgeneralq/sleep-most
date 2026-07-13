![sleep most banner](https://i.imgur.com/6ffpDeD.png)

# Before you start

1. **Fork** the repository to your own GitHub account, then clone your fork locally.
2. Check the [issues](https://github.com/mrgeneralq/sleep-most/issues) to see whether the work is already tracked. If not, open an issue first so it can be discussed.
3. Don't start working on issues that are already in progress.

# Good to know

Although all contributions to sleep-most are much appreciated, we will only merge features if they are of benefit to the community. If you ask to implement something that is only of added value to your own server, the pull request will be rejected.

# Contributing

We use a simple **fork and pull request** workflow — there is no `develop` branch.

1. **Fork** this repository and clone your fork.
2. Create a branch off `master`, e.g. `git checkout -b fix/short-description`.
3. Make your change, keeping it focused on a single issue or feature.
4. Build and run the tests before opening a PR — `./verify.sh` (Linux/macOS) or `verify.bat` (Windows), or `mvn -B clean verify`. This requires Maven and JDK 21.
5. Push the branch to your fork and open a **pull request against `master`** of this repository.
6. The CI pipeline (build + tests) runs automatically on your PR — please make sure it passes. A maintainer will then review and merge.

# Architecture Guidelines
*Below you can find a list of some specific architecture related guidelines you have to respect.*

## Messages

### Sending messages
Every message being sent to a player MUST use the IMessageService.sendMessage()
This is done so that we can control when a message should and should not be sent. If the message in the config is empty, it will not be sent. This can only be done if the message service is used.

*Example*

```java
import me.mrgeneralq.core.interfaces.IMessageService;
import org.bukkit.command.CommandSender;

public class A() {

    private final IMessageService messageService;

    A(IMessageService messageService) {
        this.messageService = messageService;
    }

    public void onA(CommandSender sender) {
        String myMessage = "Hello World";
        this.messageService.sendMessage(sender, myMessage);
    }
}
```

### Fetching messages from config
Every message that should be fetched and be part of `messages.yml` should be part of the `MessageMapper.class`
The MessageMapper is used to map the Enum `ConfigMessage` to a message object `Message.class`.

1. Create a new message in the `ConfigMessage.class`
2. Register the message in the message mappe

**MessageMapper**
```java
public class MessageMapper {
    public void loadMessages() {
        //add your message key HERE
        this.messages.put(ConfigMessage.YOUR_MESSAGE_KEY, new Message("your.message.path", "default value of message"));
    }
}
```
To fetch the message, you can use the `IMessageService.getMessage(ConfigMessage message)`.

```java
import me.mrgeneralq.core.builders.MessageBuilder;
import me.mrgeneralq.core.enums.MessageKey;
import me.mrgeneralq.core.interfaces.IMessageService;

public class A {

    private final IMessageService messageService;

    A(IMessageService messageService) {
        this.messageService = messageService;
    }

    public void helloWorld(Player player) {
        //fetching the message builder object (this can be used to modify placeholders)
        MessageBuilder helloWorldMsg = this.messageService.getMessage(MessageKey.HELLO_WORLD);
        this.messageService.sendMessage(player, helloWorldMsg.build());
    }
}
```


