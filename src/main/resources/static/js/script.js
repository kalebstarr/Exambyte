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
                <div class="row mb-3">
                  <label for="option1" class="col-sm-2 col-form-label">Option 1</label>
                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="option1" name="option1" placeholder="Option 1" required />
                  </div>
                </div>
              `;
        questionContainer.appendChild(additionalContent);
      }
    } else {
      if (additionalContent) {
        additionalContent.remove();
      }
    }
  });
});