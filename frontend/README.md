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


## React Attacks
Reacts automatically treats all user inputs as text, not code unless YOU explicitly ask it not to.
- Dom Update: element.innerText = "<script>alert('Hacked')</script>"
- Not element.innerHTML = "<script>alert('Hacked')</script>"
- This is why React is safe.

React ALWAYS uses innerText, never innerHTML. In this way XSS attacks are prevented.

## Attack happen
🛑 So when does XSS happen in React?

ONLY when you bypass React’s safety.

Example:

<div dangerouslySetInnerHTML={{ __html: comment }} />


This tells React:

“Don’t protect me.
Insert this as raw HTML, even if it contains <script>.”

Then the browser sees:

<div><script>alert()</script></div>


and it executes.

That’s XSS. 
So React is safe unless you explicitly disable safety. React prevents XSS because React escapes user input and inserts it using .textContent internally.