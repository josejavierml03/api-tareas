"""Genera una web navegable a partir de las memorias Markdown."""

from html import escape
from pathlib import Path
import markdown
import shutil

ROOT = Path(__file__).resolve().parent
SITE = ROOT / "site"
STYLE = "body{max-width:960px;margin:2rem auto;padding:0 1rem;font:17px/1.6 system-ui}pre{overflow:auto;padding:1rem;background:#eee}table{border-collapse:collapse}td,th{border:1px solid #ccc;padding:.4rem}img{max-width:100%}"


def page(title, content):
    return f'<!doctype html><html lang="es"><meta charset="utf-8"><meta name="viewport" content="width=device-width"><title>{escape(title)}</title><style>{STYLE}</style><nav><a href="index.html">Memorias</a></nav><main>{content}</main></html>'


def build():
    SITE.mkdir(exist_ok=True)
    for directory in ROOT.iterdir():
        if directory.is_dir() and directory.name not in {"site", "__pycache__"}:
            shutil.copytree(directory, SITE / directory.name, dirs_exist_ok=True)
    for image in ROOT.glob("*.svg"):
        shutil.copyfile(image, SITE / image.name)
    links = []
    for source in sorted(ROOT.glob("*.md")):
        text = source.read_text(encoding="utf-8")
        title = text.splitlines()[0].lstrip("# ")
        target = source.with_suffix(".html").name
        content = markdown.markdown(text, extensions=["tables", "fenced_code", "toc"])
        (SITE / target).write_text(page(title, content), encoding="utf-8")
        links.append(f'<li><a href="{target}">{escape(title)}</a></li>')
    (SITE / "index.html").write_text(page("API de tareas — memorias", "<h1>Memorias de los boletines</h1><ul>" + "".join(links) + "</ul>"), encoding="utf-8")


if __name__ == "__main__":
    build()
