![sleep most banner](https://i.imgur.com/6ffpDeD.png)

# Before you start ...
1. Clone the repo to your local computer.
2. Check on the Project Board if there is already an issue. If not, create an issue first.
3. Don't start working on Issues that are already in progress

# Good to know
Although all contributions to sleep-most are much appreciated, we will only merge features if they are of benefit to the community.
If you ask to implement something that is only of added value to your server, the Pull request will be rejected.

# Contributing

## Creating a feature-branch
We make use of the `Git flow` approach. Therefore, always create a branch based on `develop` branch. Whenever you are done, you may submit a PR to merge it with the open release branch. After that, we will merge your changes back into the `develop` branch. Do NOT submit a PR to merge with master. We will not accept that.

## Hotfixes
Hotfixes are issues that could or will have an impact on the existing plugin. We consider hotfixes **Major issues** that need instant resolution. Whenever you have a major issue, you create a feature-branch based on the `hotfix` branch. Submit a Pull request to merge into `hotfix` to collect all hotfixes.
Once all of it is done, we will merge the hotfix branch with a priority into the `master` branch.

> ⚠️Only major issues are accepted for hotfixes. If there are small bugs, please use the `develop` branch instead.

# Architecture Guidelines
*Below you can find a list of some specific architecture related guidelines you have to respect.*

## Messages

### Sending messages
Every message being sent to a player MUST use the IMessageService.sendMessage()
This is done so that we can control when a message should and should not be sent. If the message in the config is empty, it will not be sent. This can only be done if the message service is used.

*Example*
```java
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
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
import me.mrgeneralq.shared.messaging.MessageBuilder;
import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;

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


