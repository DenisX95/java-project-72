@import hexlet.code.dto.BasePage
@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@param Content content
@param BasePage page = null

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Анализатор страниц</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

    </head>
    <body class="d-flex flex-column min-vh-100 bg-light text-dark">
        <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
            <div class="container-xl">
                <a class="navbar-brand fw-bold" href="/">Анализатор страниц</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <div class="navbar-nav ms-auto">
                        <a class="nav-link" href="${NamedRoutes.rootPath()}">Главная</a>
                        <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
                    </div>
                </div>
            </div>
        </nav>
    <main class="flex-grow-1">
        @if(page != null && page.getFlash() != null)
            <div class="alert alert-dismissible fade show alert-${page.getFlashType()} rounded-3 mx-auto mt-3 w-100 w-md-75" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        ${content}
    </main>
    <footer class="footer mt-auto py-4 border-top bg-white text-center small text-muted">
        <div class="container-xl">
            created by
            <a href="https://github.com/" target="_blank" class="text-decoration-none">DenisX95</a>
        </div>
    </footer>
    </body>
</html>