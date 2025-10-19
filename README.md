# 🚄 Valet

<div align="center">

![Valet Banner](https://via.placeholder.com/1200x300/000000/00D9FF?text=Valet+%7C+Lightning+Fast+GitHub+Deployments)

**A Vercel-like deployment system that automatically syncs your GitHub repositories**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)
[![GitHub](https://img.shields.io/badge/GitHub-API-181717?style=for-the-badge&logo=github&logoColor=white)](https://docs.github.com/en/rest)

[Features](#-features) • [Installation](#-installation) • [Configuration](#-configuration) • [Usage](#-usage) • [Contributing](#-contributing)

</div>

---

## ✨ Features

<table>
<tr>
<td>

### 🎯 **Incremental Updates**
Only fetches files that have changed in the latest commits, saving bandwidth and time.

</td>
<td>

### ⚡ **Lightning Fast**
Efficiently syncs repositories using GitHub's API with smart caching.

</td>
</tr>
<tr>
<td>

### 🔄 **Auto-Sync**
Continuously monitors for changes and deploys automatically.

</td>
<td>

### 🛡️ **Secure**
Supports GitHub token authentication for private repositories.

</td>
</tr>
<tr>
<td>

### 📦 **Easy Setup**
Simple JSON configuration with sensible defaults.

</td>
<td>

### 🌈 **Pretty CLI**
Colorful console output for better visibility.

</td>
</tr>
</table>

---

## 🎬 Quick Start

```bash
# Download the latest release
wget https://github.com/yourusername/valet/releases/latest/download/valet.jar

# Run Valet
java -jar valet.jar
```

On first run, Valet will create a `configuration.json` file. Edit it with your repository details and restart.

---

## 📦 Installation

### Prerequisites

- Java 21 or higher
- GitHub Personal Access Token (for private repos)

### Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/valet.git
cd valet

# Build with Gradle
./gradlew shadowJar

# Run the application
java -jar build/libs/valet-1.0-SNAPSHOT.jar
```

---

## ⚙️ Configuration

Valet uses a simple JSON configuration file to store artifacts:

```json
{
  "id": "template",
  "location": "/home/Template",
  "owner": "98ping",
  "repo": "Valet",
  "branch": "master"
}
```

### Configuration Options

| Option            | Type | Description                              |
|-------------------|------|------------------------------------------|
| `id`               | String | The name of your artifact                |
| `owner`           | String | Repository owner/organization            |
| `repo`            | String | Repository name                          |
| `branch`          | String | Branch to track (default: `main`)        |
| `targetDirectory` | String | Local directory to sync files to         |

---

## 🎯 Usage

### Basic Usage

```bash
java -jar valet.jar
```

## 🏗️ How It Works

```mermaid
graph LR
    A[GitHub Repository] -->|Commit| B[Valet Monitors]
    B -->|Detects Changes| C[Fetch Diff]
    C -->|Download Files| D[Update Local Directory]
    D -->|Deploy| E[Your Application]
    style A fill:#181717,color:#fff
    style B fill:#7F52FF,color:#fff
    style C fill:#00D9FF,color:#000
    style D fill:#00D9FF,color:#000
    style E fill:#10B981,color:#fff
```

1. **Monitor**: Valet continuously checks for new commits
2. **Analyze**: Identifies added, modified, and removed files
3. **Sync**: Downloads only changed files via GitHub API
4. **Deploy**: Updates your local directory with the latest changes

---

## 🛠️ Advanced Features

### Custom File Filters

```json
{
  "filters": {
    "include": ["src/**/*.kt", "*.md"],
    "exclude": ["*.tmp", "build/"]
  }
}
```

### Rollback Support

```bash
# Future feature
valet rollback --commit abc123
```

---

## 🤝 Contributing

We welcome contributions! Here's how you can help:

1. 🍴 Fork the repository
2. 🔧 Create a feature branch: `git checkout -b feature/amazing-feature`
3. 💾 Commit your changes: `git commit -m 'Add amazing feature'`
4. 📤 Push to the branch: `git push origin feature/amazing-feature`
5. 🎉 Open a Pull Request

---

## 📊 Project Stats

<div align="center">

![GitHub stars](https://img.shields.io/github/stars/yourusername/valet?style=social)
![GitHub forks](https://img.shields.io/github/forks/yourusername/valet?style=social)
![GitHub issues](https://img.shields.io/github/issues/yourusername/valet)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/valet)

</div>

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Built with [Kotlin](https://kotlinlang.org/)
- Powered by [GitHub API](https://docs.github.com/en/rest)
- Inspired by [Vercel](https://vercel.com/)

---

<div align="center">

**Made with ❤️ by [Matrix Studios](https://matrixstudios.ltd)**

[Report Bug](https://github.com/yourusername/valet/issues) • [Request Feature](https://github.com/yourusername/valet/issues)

</div>