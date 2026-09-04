npm install -g @github/copilot
npm uninstall -g @github/copilot

## cpm config for making it work with openrouter
https://github.com/burkeholland/cpm
- install cpm
- curl -fsSL https://raw.githubusercontent.com/burkeholland/cpm/main/install.sh | bash
- Modify FILEPATH: ~/.config/cpm/models.json as below: ( this worked for me!)

```json
{
  "providers": [
    {
      "name": "OpenRouter",
      "base_url": "https://openrouter.ai/api/v1",
      "provider_type": "openai",
      "api_key_env": "OPENROUTER_API_KEY",
      "models": [
        {
          "id": "deepseek/deepseek-v4-flash-0731",
          "max_prompt_tokens": 100000,
          "max_output_tokens": 15000
        }
      ]
    }
  ]
}
```
- Edit using: 
- EDITOR="code" cpm edit
---
- type `cpm`
- choose the openrouter based model and you are good to go!

---
Models that worked:
- openai/gpt-4o-mini
- 