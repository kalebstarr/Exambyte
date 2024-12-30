document.addEventListener('DOMContentLoaded', function () {
  const questionType = document.getElementById('questionType');
  const questionContainer = document.getElementById('questionContainer');

  questionType.addEventListener('change', function () {
    const selectedType = questionType.value;
    let additionalContent = document.getElementById('additionalContent');

    if (selectedType === 'Multiple Choice') {
      if (!additionalContent) {
        additionalContent = document.createElement('div');
        additionalContent.id = 'additionalContent';
        additionalContent.innerHTML = `
                <hr>
                <div id="optionContainer">
                  <div class="row mb-3">
                    <label for="option1" class="col-sm-2 col-form-label">Option 1</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="option1" name="options[]" placeholder="Option 1" required />
                    </div>
                  </div>
                </div>
                <a id="add-option" class="btn btn-success" href="#">Add option</a>
              `;
        questionContainer.appendChild(additionalContent);

        const addOption = document.getElementById('add-option');
        const optionContainer = document.getElementById('optionContainer');
        let optionCount = 1;

        addOption.addEventListener('click', function (event) {
          event.preventDefault();
          optionCount++;

          const newOption = document.createElement('div');
          newOption.classList.add('row', 'mb-3');
          newOption.innerHTML = `
                  <label for="option${optionCount}" class="col-sm-2 col-form-label">Option ${optionCount}</label>
                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="option${optionCount}" name="options[]" placeholder="Option ${optionCount}" required />
                  </div>
               `;
          optionContainer.appendChild(newOption);
        });
      }
    } else {
      if (additionalContent) {
        additionalContent.remove();
      }
    }
  });
});1