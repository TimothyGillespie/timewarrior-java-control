# Timewarrior Java Control
[![CI](https://github.com/TimothyGillespie/timewarrior-java-control/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/TimothyGillespie/timewarrior-java-control/actions/workflows/main.yml)

### Overview of the timewarrior commands

Assume `Timewarrior tw = new Timewarrior()`

| TimeWarrior Command | Java Control |
| --- | --- |
| `timew --version` | `tw.getVersion()` |
| `timew annotate @<id> [@<id> ...] <annotation>` | not yet implemented |
| `timew cancel` | not yet implemented |
| `timew continue` | not yet implemented |
| `timew day [<interval>] [<tag> ...]` | not yet implemented |
| `timew delete @<id> [@<id> ...]` | not yet implemented |
| `timew diagnostics` | not yet implemented |
| `timew export [<interval>] [<tag> ...]` | not yet implemented |
| `timew gaps [<interval>] [<tag> ...]` | not yet implemented |
| `timew get <DOM> [<DOM> ...]` | partially with `tw.get(<DOM>)` |
| `timew help [...]` | won't implement |
| `timew join @<id> @<id>` | not yet implemented |
| `timew lengthen @<id> [@<id> ...] <duration>` | not yet implemented |
| <code>timew modify (start&#124;end) @&#60;id&#62; &#60;date&#62;</code> | not yet implemented |
| `timew month [<interval>] [<tag> ...]` | not yet implemented |
| `timew move @<id> <date>` | not yet implemented |
| `timew [report] <report> [<interval>] [<tag> ...]` | not yet implemented |
| `timew shorten @<id> [@<id> ...] <duration>` | not yet implemented |
| `timew show` | not yet implemented |
| `timew split @<id> [@<id> ...]` | not yet implemented |
| `timew start [<date>] [<tag> ...]` | Partially with `tw.start(<tag> ...)` |
| `timew stop [<tag> ...]` | not yet implemented |
| `timew summary [<interval>] [<tag> ...]` | not yet implemented |
| `timew tag @<id> [@<id> ...]` | not yet implemented |
| `timew tags [<interval>] [<tag> ...]` | not yet implemented |
| `timew track [<interval>] [<tag> ...]` | not yet implemented |
| `timew undo` | not yet implemented |
| `timew untag @<id> [@<id> ...] <tag> [<tag ...]` | not yet implemented |
| `timew week <interval> [<tag ...]` | not yet implemented |


### DOM information retrieval

Assume `n` is a number like 1. More information is available here: https://timewarrior.net/reference/timew-dom.7/

| DOM Path | Java Control | Return type |
| --- | --- | --- |
| `dom.tag.count` | `tw.getTotalTagCount()` | `Integer` |
| `dom.tag.n` | `tw.getNthTag(n)` | [`Tag`](https://github.com/TimothyGillespie/timewarrior-java-control/blob/main/src/main/java/eu/gillespie/timewarriorcontrol/Tag.java) |
||
| `dom.active` | `tw.isTracking()` | `boolean` |
| `dom.active.tag.count` | not yet implemented | `Integer` |
| `dom.active.tag.n` | not yet implemented | [`Tag`](https://github.com/TimothyGillespie/timewarrior-java-control/blob/main/src/main/java/eu/gillespie/timewarriorcontrol/Tag.java) |
| `dom.active.start` | not yet implemented | not yet determined |
| `dom.active.duration` | not yet implemented | not yet determined |
| `dom.active.json` | `tw.getActiveTracking()` | [`Tracking`](https://github.com/TimothyGillespie/timewarrior-java-control/blob/main/src/main/java/eu/gillespie/timewarriorcontrol/Tracking.java) |
||
| `dom.tracked` | not yet implemented | `Integer` |
| `dom.tracked.n.tag.count` | not yet implemented | `Integer` |
| `dom.tracked.n.tag.n` | not yet implemented | [`Tag`](https://github.com/TimothyGillespie/timewarrior-java-control/blob/main/src/main/java/eu/gillespie/timewarriorcontrol/Tag.java) |
| `dom.tracked.n.start` | not yet implemented | not yet determined |
| `dom.tracked.n.end` | not yet implemented | not yet determined |
| `dom.tracked.duration` | not yet implemented | not yet determined |
| `dom.tracked.json` | not yet implemented | not yet determined |
||
| `dom.rc.<name>` | not yet implemented | `String` |
