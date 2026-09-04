---
description: BDD (given-when-then) test method naming convention for unit tests
applyTo: '**/*Test.java'
---

# BDD Test Method Naming

Name unit test methods using the BDD **given-when-then** convention in camelCase, with underscores separating the three segments.

## Format

```
given<Context>_when<Action>_then<ExpectedOutcome>
```

Each segment is written in camelCase and describes a single, concrete scenario.

## Rules

- Start with `given...` describing the setup/context/state.
- Follow with `when...` describing the action or method under test.
- End with `then...` describing the expected result or assertion.
- Keep names descriptive and readable as a sentence when underscores are replaced with spaces.
- The method body should not be renamed to match — only the method name follows this convention.

## Examples

```java
@Test
public void givenValidDirectoryWithFilesAndSubdir_whenFindStringPath_thenReturnsFileAndDirectoryEntries() { ... }

@Test
public void givenNonExistentPath_whenFindStringPath_thenReturnsEmptyArray() { ... }

@Test
public void givenEmptyList_whenGetMax_thenReturnsEmptyOptional() { ... }
```

## Anti-patterns (avoid)

```java
public void testFindStringPathReturnsFilesAndDirectories() { ... }   // "test"-prefixed, not BDD
public void findStringPath_returnsFilesAndDirectories() { ... }      // missing given/when/then segments
public void givenStuff_whenDoStuff_thenItWorks() { ... }             // vague, non-concrete wording
```

## Learnings

- Adopt this convention consistently for all `*Test.java` files in the repo so test intent is self-documenting and readable in IDE test explorers.
