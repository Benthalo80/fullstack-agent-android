# Design Arena → Android Clone Spec

Source: `https://www.designarena.ai/` (Next.js on Vercel, Supabase, `docs.designarena.ai` API).
Target repo: `fullstack-agent-android` — native companion: browse/vote leaderboards offline-first.

## 1. Site → App map

| Web route | Android screen | Notes |
|---|---|---|
| `/?category=X` hero + 13 biscuits | `HomeScreen` | prompt field + category grid; `Mobile App`/`Video` show “Coming back soon” chip (matches web disabled state) |
| `/leaderboard/{arena}/{category}` (32 cats) | `LeaderboardScreen` | Elo / WinRate toggle + Preference vs Speed/Price scatter (MPAndroidChart or Compose Canvas) + task sub-buckets |
| `/models`, `/models/compare`, `/models/[slug]` (203) | `ModelsScreen` + `ModelDetailScreen` | filters: Input-to-Output, Arena, Provider, License; card: Elo, Top Arena, $/1M, context, tags |
| Tournament Battle A vs B → Winners/Losers/Tie-break (5 votes) | `BattleScreen` | anon A/B cards, vote → next pairing, final 1-4 ranking; stored locally, POST when backend exists |
| `/about` methodology | `AboutScreen` (static) | Bradley-Terry text: 200 iters, Δ<0.0001, Rating=400·log10(strength), <50 hidden, ~200 reliable |
| `/changelog`, `/blog` | `ChangelogScreen` (WebView / Room cache) | |
| `docs.designarena.ai` API | `DesignArenaApi` (Retrofit) | `sk_live_…` via `local.properties` / BuildConfig, never in git |

## 2. API client

Base: `https://www.designarena.ai/api/v1`, header `Authorization: Bearer sk_live_…`

```
GET /arenas                              → List<Arena>            (cache 1h)
GET /models                              → List<DaModel>         (cache 5m)
GET /leaderboard/{arena}/{category}      → LeaderboardResponse  (cache 5m)
  arena: models|builders|agents
  models cats: website,gamedev,3d,dataviz,uicomponent,image,logo,graphicdesign,
    svg,ascii,video,videotovideo,imagetovideo,multitovideo,multimodaltovideo,
    imagetoimage,slides,tts,sts,music,conversation,stt,worldmodel,codecategories
  builders cats: website,mobile
  agents cats: agon_webapps,mobileapps,nativeapps,fullstack,agentic_gamedev,agon_godot,agentic_3d,agon_slides
  entry: {displayName, provider, openRouterId:String?, elo:Int, winRate:Float, avgGenerationTimeMs:Long?}
```

Free for personal/commercial with attribution (`Credit Design Arena + link`). No hard rate-limit; CDN `s-maxage=300/3600`.
Pricing enrichment: `openRouterId` → `GET https://openrouter.ai/api/v1/models` (no key for catalog).

## 3. Room schema (offline-first)

```kotlin
@Entity("arenas") data class ArenaEntity(@PrimaryKey val id:String, val displayName:String, val categoriesJson:String, val updatedAt:Long)
@Entity("models") data class ModelEntity(@PrimaryKey val slug:String, val displayName:String, val provider:String,
  val openRouterId:String?, val license:String, val contextWindow:Int?, val maxOutput:Int?, val inputCost:Double?, val outputCost:Double?, val tagsJson:String)
@Entity("leaderboard_entries")
data class LeaderboardEntryEntity(@PrimaryKey(autoGenerate=true) val id:Long=0,
  val arena:String, val category:String, val displayName:String, val provider:String,
  val openRouterId:String?, val elo:Int, val winRate:Float, val avgGenMs:Long?, val rank:Int, val fetchedAt:Long)
@Entity("votes") data class VoteEntity(@PrimaryKey(autoGenerate=true) val id:Long=0,
  val battleId:String, val arena:String, val category:String, val winner:String, val loser:String, val createdAt:Long, val synced:Int=0)
```

DAOs: `ArenaDao (upsert/get)`, `ModelDao (paging by arena/provider/search)`, `LeaderboardDao (entries by arena+category ordered by elo, clear+insert TX)`, `VoteDao (unsynced queue)`.
DB: `ArenaDatabase v1`, `fallbackToDestructiveMigration`, `allowMainThreadQueries=false`.

## 4. Screens (Compose + Navigation-Compose)

`NavGraph: home → leaderboard/{arena}/{category} → models → model/{slug} → battle → about`
- `HomeScreen`: prompt TextField, `LazyVerticalGrid` biscuits (icon + label), recent leaderboards (Room Flow).
- `LeaderboardScreen`: arena/category dropdowns, Elo/WinRate `SegmentedButton`, `LazyColumn` rows (rank, name, provider, Elo, win%, ms), pull-refresh → `Repo.refreshLeaderboard()`.
- `ModelsScreen`: search + FilterChips (provider/arena/license), `LazyColumn` cards → detail bottom-sheet (cost, context, tags, OpenRouter link).
- `BattleScreen`: two anonymous cards, `Vote A/B` → ViewModel advances bracket (SF: semi1, semi2, winners, losers, tiebreak) → final ranking dialog.
- `AboutScreen`: methodology static text.

## 5. Build / secrets

- `local.properties`: `designArenaKey=sk_live_…` → `BuildConfig.DESIGN_ARENA_KEY` (OkHttp interceptor). No key → cached-only mode.
- Min 26, target 36. Retrofit2 + OkHttp + kotlinx.serialization, Room KSP, Lifecycle-ViewModel, Navigation-Compose, Coil, Coroutines.
- Attribution footer on every leaderboard list: “Rankings by Design Arena — designarena.ai”.
