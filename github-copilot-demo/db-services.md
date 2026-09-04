# MySQL & Redis Service Commands (WSL)

Both run as **systemd services** in WSL.

## Quick Reference

| Action | MySQL | Redis |
|---|---|---|
| Stop (now) | `sudo systemctl stop mysql` | `sudo systemctl stop redis-server` |
| Start | `sudo systemctl start mysql` | `sudo systemctl start redis-server` |
| Restart | `sudo systemctl restart mysql` | `sudo systemctl restart redis-server` |
| Status | `systemctl status mysql` | `systemctl status redis-server` |
| Disable auto-start | `sudo systemctl disable mysql` | `sudo systemctl disable redis-server` |
| Re-enable auto-start | `sudo systemctl enable mysql` | `sudo systemctl enable redis-server` |

> **Note:** Both are `enabled`, so they auto-start on WSL boot. Use `stop` to pause for the session; use `disable --now` to stop now **and** prevent auto-start on future boots.

## Aliases

Add these to your shell config (`~/.bashrc` or `~/.zshrc`), then run `source ~/.bashrc` (or `source ~/.zshrc`).

```bash
# ---- MySQL ----
alias mysql-stop='sudo systemctl stop mysql'
alias mysql-start='sudo systemctl start mysql'
alias mysql-restart='sudo systemctl restart mysql'
alias mysql-status='systemctl status mysql'
alias mysql-disable='sudo systemctl disable --now mysql'
alias mysql-enable='sudo systemctl enable --now mysql'

# ---- Redis ----
alias redis-stop='sudo systemctl stop redis-server'
alias redis-start='sudo systemctl start redis-server'
alias redis-restart='sudo systemctl restart redis-server'
alias redis-status='systemctl status redis-server'
alias redis-disable='sudo systemctl disable --now redis-server'
alias redis-enable='sudo systemctl enable --now redis-server'

# ---- Both at once ----
alias db-stop='sudo systemctl stop mysql redis-server'
alias db-start='sudo systemctl start mysql redis-server'
alias db-restart='sudo systemctl restart mysql redis-server'
alias db-status='systemctl status mysql redis-server'
alias db-disable='sudo systemctl disable --now mysql redis-server'
alias db-enable='sudo systemctl enable --now mysql redis-server'
```

## Usage Examples
```bash
mysql-stop        # stop MySQL only
redis-restart     # restart Redis only
db-status          # check both
db-disable         # stop both now + disable auto-start
```