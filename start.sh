#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
PORT="${PORT:-3000}"
PROJECT_ROOT="$(pwd)"
DIST_DIR="$PROJECT_ROOT/dist"
/usr/bin/time -p mkdir -p "$DIST_DIR" "${OPENCODE_WEB_DIR:?}"
/usr/bin/time -p test -f "$DIST_DIR/index.html"
/usr/bin/time -p bash -c 'test ! -f package.json || npm install --no-audit --no-fund'
/usr/bin/time -p bash -c 'test ! -f package.json || npm run build --if-present'
/usr/bin/time -p node -e 'const fs=require("fs");const path=require("path");const root=process.cwd();const dist=path.join(root,"dist");const out=process.env.OPENCODE_WEB_DIR+"/deployment-output.json";fs.writeFileSync(out,JSON.stringify({project:root,directory:dist}));console.log("deployment-output:",fs.readFileSync(out,"utf8"))'
/usr/bin/time -p cat "${OPENCODE_WEB_DIR:?}/deployment-output.json"
/usr/bin/time -p node --input-type=module -e '
import { createServer } from "node:http";
import { readFileSync, statSync, existsSync } from "node:fs";
import { join, resolve, extname } from "node:path";
const root = resolve(process.cwd(), "dist");
const port = Number(process.env.PORT || "3000");
const mime = { ".html":"text/html", ".js":"application/javascript", ".css":"text/css", ".json":"application/json", ".svg":"image/svg+xml", ".png":"image/png", ".jpg":"image/jpeg", ".webp":"image/webp", ".wasm":"application/wasm" };
const server = createServer((req, res) => {
  try {
    const url = new URL(req.url, "http://localhost");
    let p = resolve(root, "." + decodeURIComponent(url.pathname));
    if (p !== root && !p.startsWith(root + "/")) { res.writeHead(404); res.end("Not found"); return; }
    try { if (statSync(p).isDirectory()) p = join(p, "index.html"); } catch { p = join(root, "index.html"); }
    if (!existsSync(p)) p = join(root, "index.html");
    res.setHeader("Content-Type", mime[extname(p)] || "application/octet-stream");
    res.setHeader("Cache-Control", "no-cache");
    res.end(readFileSync(p));
  } catch { res.writeHead(404); res.end("Not found"); }
});
server.listen(port, "0.0.0.0", () => console.log(`serving ${root} on :${port}`));
'
