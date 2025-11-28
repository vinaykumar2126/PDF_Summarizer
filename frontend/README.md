⭐ JavaScript Async/Await vs Web Workers — Difference Note
🟦 1. JavaScript is Single-Threaded

Only one main thread
That one thread handles:
React rendering
DOM updates
Event handling

Your JavaScript code

If the main thread is blocked → UI freezes.

🟢 ASYNC/AWAIT (for non-blocking I/O)
✔ What it’s for:

Network requests (fetch)
Timers (setTimeout)
Database calls
File reading (browser APIs)
Anything that waits for something external

✔ How it works:

Does NOT create new threads
Does NOT run in parallel
ONLY pauses the function, not the thread

Browser handles the slow work (network, timers) in background threads

✔ Main benefit:

Prevents UI freezing during slow operations.
The UI stays responsive.

✔ Good example:
const data = await fetch("server.com");

✔ Key idea:

Async/await solves waiting problems (I/O), NOT performance problems (CPU)