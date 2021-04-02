hack.jar
========

Hacking tips.

Inspired by and based on [aji](https://github.com/aji/)'s weechat script [hack.py](https://github.com/aji/shenanigans/blob/master/hack.py).
All credit for the content in `hack.json` and the general algorithm used goes to him.

[Fuck the GPL](https://web.archive.org/web/20200807183044/https://mod16.org/hurfdurf/?p=101)

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
