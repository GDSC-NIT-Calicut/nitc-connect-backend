Your commits should be a manageable size. We don't want to make a commit every time we change a file; that's just useless. 
The whole point of committing is to keep checkpoints as we go.
So if we screw up, we can always go back and recover the code.
As you feel like you have reached a state, you want to record the change and then make a commit.
Each commit should also represent a logically different chain set. So, keep things from mixing up.

Creating clear commit guidelines is essential for maintaining a well-organized and understandable project history.
Here’s a structured set of commit guidelines that you can use for your organization:

### Commit Guidelines

#### 1. **Commit Message Structure**
Each commit message should have a clear structure to provide context and understanding of the changes made. Use the following format:

```
<type>(<scope>): <subject>
```

- **type**: Specifies the nature of the commit. Use one of the following:
  - `feat`: A new feature
  - `fix`: A bug fix
  - `docs`: Documentation changes
  - `style`: Changes that do not affect the meaning of the code (formatting, missing semi-colons, etc.)
  - `refactor`: Code changes that neither fix a bug nor add a feature
  - `test`: Adding missing tests or correcting existing tests
  - `chore`: Changes to the build process or auxiliary tools and libraries
  - `perf`: A code change that improves performance

- **scope**: A noun describing what the commit affects (optional). For example, `api`, `frontend`, `backend`, `auth`, etc.

- **subject**: A short description of the change, not exceeding 50 characters. Use the imperative mood (e.g., "Add feature", not "Added feature").

#### 2. **Commit Message Body (Optional)**
If the commit requires more explanation, add a body to describe the motivation and context for the change. The body should:
- Use the imperative, present tense: "Change", not "Changed" or "Changes".
- Include any relevant information, links, or issue numbers.
- Wrap text at 72 characters.

**Example:**
```
feat(auth): add password reset functionality

This commit introduces a password reset feature using the OTP mechanism. 
It adds a new API endpoint and modifies the user model to include a token field.

Fixes #1234
```

#### 3. **Atomic Commits**
- Make small, self-contained commits that focus on a single change. This improves readability and makes it easier to find specific changes in the history.

#### 4. **Avoid WIP (Work In Progress) Commits**
- Do not push incomplete or work-in-progress commits to the main branch. Use feature branches for ongoing work.

#### 5. **Reference Issues and PRs**
- Reference related issues or pull requests in the commit message to create links between code changes and the discussions that led to them. Use keywords like `Closes #123` or `Fixes #456`.

#### 6. **Commit Size**
- Commit often to save progress and make it easier to track changes. Each commit should be a meaningful checkpoint.

### Example Commit Messages

1. **New Feature:**
   ```
   feat(api): add user profile endpoint
   ```
   
2. **Bug Fix:**
   ```
   fix(auth): correct token expiration logic
   ```
   
3. **Documentation:**
   ```
   docs: update API usage examples in README
   ```
   
4. **Refactoring:**
   ```
   refactor(database): optimize query for fetching user data
   ```

5. **Style Changes:**
   ```
   style(css): improve button spacing and font size
   ```
