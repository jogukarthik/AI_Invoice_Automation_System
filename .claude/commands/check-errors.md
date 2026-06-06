# check-errors

When the user asks "what is the error", "check logs", "find the error", or any variation:

## Step 1 — Collect logs
- Check for `*.log` files in the project
- If none, run the app (`./mvnw spring-boot:run`) and capture stdout/stderr
- Do NOT apply any fixes yet

## Step 2 — Report in this exact format

### Problem
One clear sentence describing what failed and where (e.g., which bean, which file, which line).

### Root Cause
Why it failed — explain the underlying reason, not just the symptom.

### Fix
Numbered list of options ranked by preference (safest/least destructive first).
For each option:
- **Option N — Name**: what to do, one sentence
- Note any side effects or when to prefer this option

### Recommended
State which option to use for local dev vs production.

## Step 3 — Wait
Do NOT apply any fix until the user explicitly says "fix it with option N" or "apply the fix".
