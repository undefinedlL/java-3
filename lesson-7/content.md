# Создание основного каркаса проекта (ReactJS + CSS)

## Структура папки ./src/
```sh
./src
├── App.jsx
├── components
│   ├── Footer.jsx
│   └── Header.jsx
├── main.jsx
├── pages
│   └── Home.jsx
└── styles
    ├── Footer.css
    ├── global.css
    ├── Header.css
    └── Home.css
```

# ./src/components/Header.jsx
```
import "./Header.css";

function Header() {
  return (
    <header className="header">
      <div className="logo"><Link to="/">Винни-Пух & Co</Link></div>

      <nav className="nav">
        <a href="/">Главная</a>
        <a href="/">Товары</a>
        <a href="/">Контакты</a>
        <a href="/">Заказать</a>
      </nav>
    </header>
  );
}

export default Header;
```

# ./src/styles/Header.css
```
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #FFF8EC;
  padding: 20px 40px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
}
.logo {
  font-size: 24px;
  font-weight: 700;
  color: #8B5E3C;
}
.nav a {
  margin-left: 24px;
  text-decoration: none;
  color: #8B5E3C;
  font-weight: 500;
  position: relative;
  transition: color 0.3s ease;
}
```

# ./src/components/Footer.jsx
```
import "../styles/Footer.css";

function Footer() {
  return (
    <footer className="footer">
      <p>© {new Date().getFullYear()} Винни-Пух и Компания</p>
    </footer>
  );
}

export default Footer;
```

# ./src/styles/Footer.css
```
.footer {
  margin-top: 40px;
  padding: 20px;
  text-align: center;
  background: #FFF8EC;
  color: #8B5E3C;
  font-size: 14px;
  border-top: 1px solid #E6CDAA;
}
```

# ./src/page/Home.jsx
```
import "../styles/Home.css";

function Home() {
  return (
    <div className="home">
      <h1>Добро пожаловать в магазин Винни-Пуха!</h1>
      <p>Здесь вы найдёте мёд, игрушки, сувениры и многое другое.</p>

      <div className="welcome-card">
        <h2>Мы рады вам!</h2>
      </div>
    </div>
  );
}
export default Home;
```

# ./src/styles/Home.css
```
.home {
  padding: 40px;
  text-align: center;
  background: #FFF8EC;
  color: #8B5E3C;
}

.welcome-card {
  margin: 40px auto;
  max-width: 500px;
  padding: 24px;
  background: #F7D488;
  border-radius: 24px;
  box-shadow: 0 6px 12px rgba(0,0,0,0.08);
}

h1 {
  font-size: 32px;
  margin-bottom: 10px;
}

p {
  font-size: 18px;
}
```

# ./src/App.jsx
```
import React from 'react';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import './styles/global.css';

export default function App(){
  return (
    <>
        <Header />
        <Home />
        <Footer />
    </>
  );
}
```