hack.jar
========

Hacking tips.

Inspired by and based on [aji](https://github.com/aji/)'s weechat script [hack.py](https://github.com/aji/shenanigans/blob/master/hack.py).
All credit for the content in `hack.json` and the general algorithm used goes to him.

![lgpl logo](https://www.gnu.org/graphics/lgplv3-with-text-154x68.png)

## Usage

```java
import org.appledash.hack.Hack;

class HackExample {
    public static void main(String[] args) {
        Hack hack = Hack.create();
        
        System.out.println(hack.getAdvice());
    }
}
```
